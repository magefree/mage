package mage.cards.f;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.PestBlackGreenDiesToken;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author muz
 */
public final class FeralAppetite extends CardImpl {

    private static final FilterPermanent filter = new FilterAttackingCreature("attacking Pests");

    static {
        filter.add(SubType.PEST.getPredicate());
    }

    public FeralAppetite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Attacking Pests you control get +1/+0 and have deathtouch.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(
            1, 0, Duration.WhileOnBattlefield,
            filter
        ));
        ability.addEffect(new GainAbilityControlledEffect(
            DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield,
            filter
        ).setText("and have deathtouch"));
        this.addAbility(ability);

        // {1}{G}: Exile target card from a graveyard. If a creature card is exiled this way, create a 1/1 black and green Pest creature token with "When this token dies, you gain 1 life."
        Ability ability2 = new SimpleActivatedAbility(new FeralAppetiteEffect(), new ManaCostsImpl<>("{1}{G}"));
        ability2.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability2);
    }

    private FeralAppetite(final FeralAppetite card) {
        super(card);
    }

    @Override
    public FeralAppetite copy() {
        return new FeralAppetite(this);
    }
}

class FeralAppetiteEffect extends OneShotEffect {

    private static final Token token = new PestBlackGreenDiesToken();

    FeralAppetiteEffect() {
        super(Outcome.Benefit);
        staticText = "Exile target card from a graveyard. If it was a creature card, " +
                "create a 1/1 black and green Pest creature token with \"When this token dies, you gain 1 life.\"";
    }

    private FeralAppetiteEffect(final FeralAppetiteEffect effect) {
        super(effect);
    }

    @Override
    public FeralAppetiteEffect copy() {
        return new FeralAppetiteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        boolean isCreature = card.isCreature(game);
        if (player.moveCards(card, Zone.EXILED, source, game) && isCreature) {
            token.putOntoBattlefield(1, game, source, source.getControllerId());
        }
        return true;
    }
}
