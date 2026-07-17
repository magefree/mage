package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MayCastTargetCardEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class HeraldOfAmity extends CardImpl {

    private static final FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent("Auras you control");

    static {
        filter.add(SubType.AURA.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public HeraldOfAmity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.GRIFFIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, exile the top eight cards of your library.
        // You may cast an Aura spell from among them without paying its mana cost.
        // Then put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new HeraldOfAmityEffect()));

        // Whenever this creature attacks, it gets +X/+X until end of turn, where X is the number of Auras you control.
        this.addAbility(new AttacksTriggeredAbility(
            new BoostSourceEffect(xValue, xValue, Duration.EndOfTurn, "it")
                .setText("it gets +X/+X until end of turn, where X is the number of Auras you control")
        ).addHint(new ValueHint("Auras you control", xValue)));
    }

    private HeraldOfAmity(final HeraldOfAmity card) {
        super(card);
    }

    @Override
    public HeraldOfAmity copy() {
        return new HeraldOfAmity(this);
    }
}

class HeraldOfAmityEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("an Aura card");

    static {
        filter.add(SubType.AURA.getPredicate());
    }

    HeraldOfAmityEffect() {
        super(Outcome.PlayForFree);
        staticText = "exile the top eight cards of your library. You may cast an Aura spell from among them "
            + "without paying its mana cost. Then put the rest on the bottom of your library in a random order";
    }

    private HeraldOfAmityEffect(final HeraldOfAmityEffect effect) {
        super(effect);
    }

    @Override
    public HeraldOfAmityEffect copy() {
        return new HeraldOfAmityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        UUID exileId = CardUtil.getExileZoneId(game, source);
        String exileZoneName = CardUtil.createObjectRelatedWindowTitle(source, game, null);
        Cards exiled = new CardsImpl(controller.getLibrary().getTopCards(game, 8));
        controller.moveCardsToExile(exiled.getCards(game), source, game, true, exileId, exileZoneName);
        exiled.retainZone(Zone.EXILED, game);

        // Find Aura cards among them
        Cards auras = new CardsImpl(exiled.getCards(filter, source.getControllerId(), source, game));

        if (!auras.isEmpty()) {
            TargetCard target = new TargetCard(0, 1, Zone.EXILED, filter);
            target.withNotTarget(true);
            controller.choose(Outcome.PlayForFree, auras, target, source, game);
            UUID chosen = target.getFirstTarget();
            if (chosen != null) {
                MayCastTargetCardEffect castEffect = new MayCastTargetCardEffect(CastManaAdjustment.WITHOUT_PAYING_MANA_COST);
                castEffect.setTargetPointer(new FixedTarget(chosen));
                castEffect.apply(game, source);
                exiled.retainZone(Zone.EXILED, game);
            }
        }

        // Put the rest on the bottom in a random order
        if (!exiled.isEmpty()) {
            controller.putCardsOnBottomOfLibrary(exiled, game, source, false);
        }

        return true;
    }
}
