package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheEleventhDoctor extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature with power 3 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 4));
    }

    public TheEleventhDoctor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIME_LORD);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // I. AM. TALKING! -- Whenever The Eleventh Doctor deals combat damage to a player, you may exile a card from your hand with a number of time counters on it equal to its mana value. If it doesn't have suspend, it gains suspend.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new TheEleventhDoctorEffect()).withFlavorWord("I. AM. TALKING!"));

        // {2}: Target creature with power 3 or less can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(new CantBeBlockedTargetEffect(Duration.EndOfTurn), new GenericManaCost(2));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private TheEleventhDoctor(final TheEleventhDoctor card) {
        super(card);
    }

    @Override
    public TheEleventhDoctor copy() {
        return new TheEleventhDoctor(this);
    }
}

class TheEleventhDoctorEffect extends OneShotEffect {

    TheEleventhDoctorEffect() {
        super(Outcome.Benefit);
        staticText = "you may exile a card from your hand with a number of time counters on it " +
                "equal to its mana value. If it doesn't have suspend, it gains suspend";
    }

    private TheEleventhDoctorEffect(final TheEleventhDoctorEffect effect) {
        super(effect);
    }

    @Override
    public TheEleventhDoctorEffect copy() {
        return new TheEleventhDoctorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInHand(0, 1, StaticFilters.FILTER_CARD);
        player.choose(Outcome.PlayForFree, player.getHand(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        SuspendAbility.addTimeCountersAndSuspend(card, card.getManaValue(), source, game);
        return true;
    }
}
