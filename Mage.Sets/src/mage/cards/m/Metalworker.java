package mage.cards.m;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author anonymous
 */
public final class Metalworker extends CardImpl {

    public Metalworker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {T}: Reveal any number of artifact cards in your hand. Add {C}{C} for each card revealed this way.
        SimpleManaAbility ability = new SimpleManaAbility(Zone.BATTLEFIELD, new MetalworkerManaEffect(), new TapSourceCost());
        ability.setUndoPossible(false);
        this.addAbility(ability);
    }

    private Metalworker(final Metalworker card) {
        super(card);
    }

    @Override
    public Metalworker copy() {
        return new Metalworker(this);
    }
}

class MetalworkerManaEffect extends ManaEffect {

    public MetalworkerManaEffect() {
        super();
        staticText = "Reveal any number of artifact cards in your hand. Add {C}{C} for each card revealed this way";
    }

    public MetalworkerManaEffect(final MetalworkerManaEffect effect) {
        super(effect);
    }

    @Override
    public MetalworkerManaEffect copy() {
        return new MetalworkerManaEffect(this);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        if (game != null) {
            Player controller = getPlayer(game, source);
            if (controller != null) {
                int artifacts = controller.getHand().count(StaticFilters.FILTER_CARD_ARTIFACT, game);
                if (artifacts > 0) {
                    netMana.add(Mana.ColorlessMana(artifacts * 2));
                }
            }
        }
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana mana = new Mana();
        if (game == null) {
            return mana;
        }
        Player controller = getPlayer(game, source);
        if (controller == null) {
            return mana;
        }
        int artifacts = controller.getHand().count(StaticFilters.FILTER_CARD_ARTIFACT, game);
        if (artifacts > 0) {
            TargetCardInHand target = new TargetCardInHand(0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_ARTIFACT);
            if (controller.choose(Outcome.Benefit, target, source, game)) {
                Cards cards = new CardsImpl(target.getTargets());
                controller.revealCards(source, cards, game);
                return Mana.ColorlessMana(target.getTargets().size() * 2);
            }
        }
        return mana;
    }
}
