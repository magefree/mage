package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BelakorTheDarkMaster extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.DEMON, "Demons you control");
    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Demons you control", xValue);
    private static final FilterPermanent filter2 = new FilterControlledPermanent(SubType.DEMON, "another Demon");

    static {
        filter2.add(AnotherPredicate.instance);
    }

    public BelakorTheDarkMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Prince of Chaos -- When Be'lakor, the Dark Master enters the battlefield, you draw X cards and you lose X life, where X is the number of Demons you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(xValue).setText("you draw X cards"));
        ability.addEffect(new LoseLifeSourceControllerEffect(xValue).concatBy("and"));
        this.addAbility(ability.withFlavorWord("Prince of Chaos").addHint(hint));

        // Lord of Torment -- Whenever another Demon enters the battlefield under your control, it deals damage equal to its power to any target.
        ability = new EntersBattlefieldControlledTriggeredAbility(new BelakorTheDarkMasterEffect(), filter2);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability.withFlavorWord("Lord of Torment"));
    }

    private BelakorTheDarkMaster(final BelakorTheDarkMaster card) {
        super(card);
    }

    @Override
    public BelakorTheDarkMaster copy() {
        return new BelakorTheDarkMaster(this);
    }
}

class BelakorTheDarkMasterEffect extends OneShotEffect {

    BelakorTheDarkMasterEffect() {
        super(Outcome.Benefit);
        staticText = "it deals damage equal to its power to any target";
    }

    private BelakorTheDarkMasterEffect(final BelakorTheDarkMasterEffect effect) {
        super(effect);
    }

    @Override
    public BelakorTheDarkMasterEffect copy() {
        return new BelakorTheDarkMasterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) getValue("permanentEnteringBattlefield");
        if (permanent == null) {
            return false;
        }
        int power = permanent.getPower().getValue();
        if (power < 1) {
            return false;
        }
        UUID targetId = getTargetPointer().getFirst(game, source);
        Permanent targetPermanent = game.getPermanent(targetId);
        if (targetPermanent != null) {
            targetPermanent.damage(power, permanent.getId(), source, game);
        }
        Player targetPlayer = game.getPlayer(targetId);
        if (targetPlayer != null) {
            targetPlayer.damage(power, permanent.getId(), source, game);
        }
        return true;
    }
}
