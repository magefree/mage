package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.UntapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrainingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VikyaScorchingStalwart extends CardImpl {

    public VikyaScorchingStalwart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Training
        this.addAbility(new TrainingAbility());

        // Hadoken—{4}{R}, {Q}, Discard a card: Ryu, World Warrior deals damage equal to his power to any target. If excess damage was dealt to a creature this way, draw a card.
        Ability ability = new SimpleActivatedAbility(new VikyaScorchingStalwartEffect(), new ManaCostsImpl<>("{4}{R}"));
        ability.addCost(new UntapSourceCost());
        ability.addCost(new DiscardCardCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability.withFlavorWord("Hadoken"));
    }

    private VikyaScorchingStalwart(final VikyaScorchingStalwart card) {
        super(card);
    }

    @Override
    public VikyaScorchingStalwart copy() {
        return new VikyaScorchingStalwart(this);
    }
}

class VikyaScorchingStalwartEffect extends OneShotEffect {

    VikyaScorchingStalwartEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals damage equal to his power to any target. " +
                "If excess damage was dealt to a creature this way, draw a card";
    }

    private VikyaScorchingStalwartEffect(final VikyaScorchingStalwartEffect effect) {
        super(effect);
    }

    @Override
    public VikyaScorchingStalwartEffect copy() {
        return new VikyaScorchingStalwartEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = source.getSourcePermanentOrLKI(game);
        if (sourcePermanent == null) {
            return false;
        }
        int amount = sourcePermanent.getPower().getValue();
        if (amount < 1) {
            return false;
        }
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
            return player != null && player.damage(amount, source, game) > 0;
        }
        if (!permanent.isCreature(game)) {
            return permanent.damage(amount, source, game) > 0;
        }
        int lethal = permanent.getLethalDamage(source.getSourceId(), game);
        permanent.damage(amount, source.getSourceId(), source, game);
        if (lethal >= amount) {
            return true;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.drawCards(1, source, game);
        }
        return true;
    }
}
