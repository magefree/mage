package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AcolyteHybrid extends CardImpl {

    public AcolyteHybrid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.TYRANID);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Heavy Rock Cutter -- Whenever Acolyte Hybrid attacks, destroy up to one target artifact. If an artifact is destroyed this way, its controller draws a card.
        Ability ability = new AttacksTriggeredAbility(new AcolyteHybridEffect());
        ability.addTarget(new TargetArtifactPermanent(0, 1));
        this.addAbility(ability.withFlavorWord("Heavy Rock Cutter"));
    }

    private AcolyteHybrid(final AcolyteHybrid card) {
        super(card);
    }

    @Override
    public AcolyteHybrid copy() {
        return new AcolyteHybrid(this);
    }
}

class AcolyteHybridEffect extends OneShotEffect {

    AcolyteHybridEffect() {
        super(Outcome.Benefit);
        staticText = "destroy up to one target artifact. " +
                "If an artifact is destroyed this way, its controller draws a card";
    }

    private AcolyteHybridEffect(final AcolyteHybridEffect effect) {
        super(effect);
    }

    @Override
    public AcolyteHybridEffect copy() {
        return new AcolyteHybridEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null || !permanent.destroy(source, game)) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        if (player != null) {
            player.drawCards(1, source, game);
        }
        return true;
    }
}
