package mage.cards.f;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FangRokusCompanion extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another target legendary creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public FangRokusCompanion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Fang attacks, another target legendary creature you control gets +X/+0 until end of turn, where X is Fang's power.
        Ability ability = new AttacksTriggeredAbility(new BoostTargetEffect(
                SourcePermanentPowerValue.NOT_NEGATIVE, StaticValue.get(0)
        ));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // When Fang dies, if he wasn't a Spirit, return this card to the battlefield under your control. He's a Spirit in addition to his other types.
        this.addAbility(new FangRokusCompanionTriggeredAbility());
    }

    private FangRokusCompanion(final FangRokusCompanion card) {
        super(card);
    }

    @Override
    public FangRokusCompanion copy() {
        return new FangRokusCompanion(this);
    }
}

class FangRokusCompanionTriggeredAbility extends DiesSourceTriggeredAbility {

    FangRokusCompanionTriggeredAbility() {
        super(new FangRokusCompanionReturnEffect());
    }

    private FangRokusCompanionTriggeredAbility(final FangRokusCompanionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FangRokusCompanionTriggeredAbility copy() {
        return new FangRokusCompanionTriggeredAbility(this);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return CardUtil
                .getEffectValueFromAbility(this, "permanentLeftBattlefield", Permanent.class)
                .filter(permanent -> !permanent.hasSubtype(SubType.SPIRIT, game))
                .isPresent();
    }
}

class FangRokusCompanionReturnEffect extends OneShotEffect {

    FangRokusCompanionReturnEffect() {
        super(Outcome.Benefit);
        staticText = "if he wasn't a Spirit, return this card to the battlefield under your control. " +
                "He's a Spirit in addition to his other types.";
    }

    private FangRokusCompanionReturnEffect(final FangRokusCompanionReturnEffect effect) {
        super(effect);
    }

    @Override
    public FangRokusCompanionReturnEffect copy() {
        return new FangRokusCompanionReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        if (player == null || card == null) {
            return false;
        }
        game.addEffect(new AddCardSubTypeTargetEffect(SubType.SPIRIT, Duration.Custom)
                .setTargetPointer(new FixedTarget(new MageObjectReference(card, game, 1))), source);
        return player.moveCards(
                card, Zone.BATTLEFIELD, source, game, true,
                false, true, null
        );
    }
}
