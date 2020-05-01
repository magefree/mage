package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DreamEater extends CardImpl {

    public DreamEater(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Dream Eater enters the battlefield, surveil 4. When you do, you may return target nonland permanent an opponent controls to its owner's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SurveilEffect(4));
        ability.addEffect(new DreamEaterEffect());
        this.addAbility(ability);
    }

    private DreamEater(final DreamEater card) {
        super(card);
    }

    @Override
    public DreamEater copy() {
        return new DreamEater(this);
    }
}

class DreamEaterEffect extends OneShotEffect {

    private static final FilterPermanent filter
            = new FilterNonlandPermanent("nonland permanent an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    DreamEaterEffect() {
        super(Outcome.Benefit);
        this.staticText = "When you do, you may return target "
                + "nonland permanent an opponent controls to its owner's hand.";
    }

    private DreamEaterEffect(final DreamEaterEffect effect) {
        super(effect);
    }

    @Override
    public DreamEaterEffect copy() {
        return new DreamEaterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new ReturnToHandTargetEffect(), true,
                "you may return target nonland permanent an opponent controls to its owner's hand"
        );
        ability.addTarget(new TargetPermanent(filter));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
