package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.condition.common.ControlArtifactAndEnchantmentCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.common.ControlArtifactAndEnchantmentHint;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OkibaSalvage extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature or Vehicle card from your graveyard");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    public OkibaSalvage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Return target creature or Vehicle card from your graveyard to the battlefield. Then put two +1/+1 counters on that permanent if you control an artifact and an enchantment.
        this.getSpellAbility().addEffect(new OkibaSalvageEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
        this.getSpellAbility().addHint(ControlArtifactAndEnchantmentHint.instance);
    }

    private OkibaSalvage(final OkibaSalvage card) {
        super(card);
    }

    @Override
    public OkibaSalvage copy() {
        return new OkibaSalvage(this);
    }
}

class OkibaSalvageEffect extends OneShotEffect {

    OkibaSalvageEffect() {
        super(Outcome.Benefit);
        staticText = "return target creature or Vehicle card from your graveyard to the battlefield. " +
                "Then put two +1/+1 counters on that permanent if you control an artifact and an enchantment";
    }

    private OkibaSalvageEffect(final OkibaSalvageEffect effect) {
        super(effect);
    }

    @Override
    public OkibaSalvageEffect copy() {
        return new OkibaSalvageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent != null && ControlArtifactAndEnchantmentCondition.instance.apply(game, source)) {
            permanent.addCounters(CounterType.P1P1.createInstance(2), source, game);
        }
        return true;
    }
}
