
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterStackObject;
import mage.filter.predicate.other.ArtifactSourcePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.target.common.TargetActivatedAbility;

/**
 *
 * @author TheElk801
 */
public final class OupheVandals extends CardImpl {

    private static final FilterStackObject filter = new FilterStackObject("ability from an artifact source");

    static {
        filter.add(ArtifactSourcePredicate.instance);
    }

    public OupheVandals(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.OUPHE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {G}, Sacrifice Ouphe Vandals: Counter target activated ability from an artifact source and destroy that artifact if it's on the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new OupheVandalsEffect(), new ManaCostsImpl<>("{G}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetActivatedAbility(filter));
        this.addAbility(ability);
    }

    private OupheVandals(final OupheVandals card) {
        super(card);
    }

    @Override
    public OupheVandals copy() {
        return new OupheVandals(this);
    }
}

class OupheVandalsEffect extends OneShotEffect {

    public OupheVandalsEffect() {
        super(Outcome.Detriment);
        this.staticText = "Counter target activated ability from an artifact source and destroy that artifact if it's on the battlefield.";
    }

    public OupheVandalsEffect(final OupheVandalsEffect effect) {
        super(effect);
    }

    @Override
    public OupheVandalsEffect copy() {
        return new OupheVandalsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetId = source.getFirstTarget();
        StackObject stackObject = game.getStack().getStackObject(targetId);
        if (targetId != null && game.getStack().counter(targetId, source, game)) {
            UUID permanentId = stackObject.getSourceId();
            if (permanentId != null) {
                Permanent usedPermanent = game.getPermanent(permanentId);
                if (usedPermanent != null) {
                    usedPermanent.destroy(source, game, false);
                }
            }
            return true;
        }

        return false;
    }
}
