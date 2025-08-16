package mage.cards.f;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.FirebendingAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FireLordSozin extends CardImpl {

    public FireLordSozin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.color.setBlack(true);
        this.nightCard = true;

        // Menace
        this.addAbility(new MenaceAbility());

        // Firebending 3
        this.addAbility(new FirebendingAbility(3));

        // Whenever Fire Lord Sozin deals combat damage to a player, you may pay {X}. When you do, put any number of target creature cards with total mana value X or less from that player's graveyard onto the battlefield under your control.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new FireLordSozinEffect()));
    }

    private FireLordSozin(final FireLordSozin card) {
        super(card);
    }

    @Override
    public FireLordSozin copy() {
        return new FireLordSozin(this);
    }
}

class FireLordSozinEffect extends OneShotEffect {

    FireLordSozinEffect() {
        super(Outcome.Benefit);
        staticText = "you may pay {X}. When you do, put any number of target creature cards with " +
                "total mana value X or less from that player's graveyard onto the battlefield under your control";
    }

    private FireLordSozinEffect(final FireLordSozinEffect effect) {
        super(effect);
    }

    @Override
    public FireLordSozinEffect copy() {
        return new FireLordSozinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        if (controller == null || !controller.chooseUse(Outcome.BoostCreature, "Pay {X}?", source, game)) {
            return false;
        }
        int xValue = controller.announceX(0, Integer.MAX_VALUE, "Announce the value for {X}", game, source, true);
        ManaCosts cost = new ManaCostsImpl<>("{X}");
        cost.add(new GenericManaCost(xValue));
        if (!cost.pay(source, game, source, source.getControllerId(), false, null)) {
            return false;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), false);
        ability.addTarget(new FireLordSozinTarget((UUID) getValue("damagedPlayer"), xValue));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}

class FireLordSozinTarget extends TargetCardInGraveyard {

    private final int xValue;

    private static final FilterCard makeFilter(UUID ownerId, int xValue) {
        FilterCard filter = new FilterCreatureCard("creature cards with total mana value " + xValue + " or less from that player's graveyard");
        filter.add(new OwnerIdPredicate(ownerId));
        return filter;
    }

    FireLordSozinTarget(UUID ownerId, int xValue) {
        super(0, Integer.MAX_VALUE, makeFilter(ownerId, xValue), false);
        this.xValue = xValue;
    }

    private FireLordSozinTarget(final FireLordSozinTarget target) {
        super(target);
        this.xValue = target.xValue;
    }

    @Override
    public FireLordSozinTarget copy() {
        return new FireLordSozinTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        return super.canTarget(playerId, id, source, game)
                && CardUtil.checkCanTargetTotalValueLimit(this.getTargets(), id, MageObject::getManaValue, xValue, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        return CardUtil.checkPossibleTargetsTotalValueLimit(
                this.getTargets(),
                super.possibleTargets(sourceControllerId, source, game),
                MageObject::getManaValue, xValue, game
        );
    }

    @Override
    public String getMessage(Game game) {
        // shows selected total
        int selectedValue = this.getTargets().stream()
                .map(game::getObject)
                .filter(Objects::nonNull)
                .mapToInt(MageObject::getManaValue)
                .sum();
        return super.getMessage(game) + " (selected total mana value " + selectedValue + ")";
    }
}
