package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.token.HumanSoldierToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GeneralsEnforcer extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.HUMAN, "Legendary Humans");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public GeneralsEnforcer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Legendary Humans you control have indestructible.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));

        // {2}{W}{B}: Exile target card from a graveyard. If it was a creature card, create a 1/1 white Human Soldier creature token.
        Ability ability = new SimpleActivatedAbility(new GeneralsEnforcerEffect(), new ManaCostsImpl<>("{2}{W}{B}"));
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);
    }

    private GeneralsEnforcer(final GeneralsEnforcer card) {
        super(card);
    }

    @Override
    public GeneralsEnforcer copy() {
        return new GeneralsEnforcer(this);
    }
}

class GeneralsEnforcerEffect extends OneShotEffect {

    private static final Token token = new HumanSoldierToken();

    GeneralsEnforcerEffect() {
        super(Outcome.Benefit);
        staticText = "Exile target card from a graveyard. If it was a creature card, " +
                "create a 1/1 white Human Soldier creature token.";
    }

    private GeneralsEnforcerEffect(final GeneralsEnforcerEffect effect) {
        super(effect);
    }

    @Override
    public GeneralsEnforcerEffect copy() {
        return new GeneralsEnforcerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
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
