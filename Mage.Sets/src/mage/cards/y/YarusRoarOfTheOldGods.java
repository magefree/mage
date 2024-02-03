package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.card.FaceDownPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author DominionSpy
 */
public final class YarusRoarOfTheOldGods extends CardImpl {

    private static final FilterCreaturePermanent filter =
            new FilterCreaturePermanent("face-down creature");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(FaceDownPredicate.instance);
    }

    public YarusRoarOfTheOldGods(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Other creatures you control have haste.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES, true)));
        // Whenever one or more face-down creatures you control deal combat damage to a player, draw a card.
        this.addAbility(new DealCombatDamageControlledTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter)
                .setTriggerPhrase("Whenever one or more face-down creatures you control deal combat damage to a player, "));
        // Whenever a face-down creature you control dies, return it to the battlefield face down under its owner's control if it's a permanent card, then turn it face up.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new YarusRoarOfTheOldGodsEffect(), false, filter, true));
    }

    private YarusRoarOfTheOldGods(final YarusRoarOfTheOldGods card) {
        super(card);
    }

    @Override
    public YarusRoarOfTheOldGods copy() {
        return new YarusRoarOfTheOldGods(this);
    }
}

class YarusRoarOfTheOldGodsEffect extends OneShotEffect {

    YarusRoarOfTheOldGodsEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "return it to the battlefield face down under its owner's control " +
                "if it's a permanent card, then turn it face up";
    }

    private YarusRoarOfTheOldGodsEffect(final YarusRoarOfTheOldGodsEffect effect) {
        super(effect);
    }

    @Override
    public YarusRoarOfTheOldGodsEffect copy() {
        return new YarusRoarOfTheOldGodsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (controller == null || card == null || !card.isPermanent(game)) {
            return false;
        }

        Ability newSource = source.copy();
        newSource.setWorksFaceDown(true);
        MageObjectReference mor = new MageObjectReference(card.getId(),
                card.getZoneChangeCounter(game) + 1, game);
        game.addEffect(new BecomesFaceDownCreatureEffect(
                null, mor, Duration.Custom,
                BecomesFaceDownCreatureEffect.FaceDownType.MANUAL), newSource);
        controller.moveCards(card, Zone.BATTLEFIELD, source, game, false, true, true, null);

        Permanent permanent = game.getPermanent(card.getId());
        if (permanent != null) {
            permanent.turnFaceUp(source, game, source.getControllerId());
        }
        return true;
    }
}
