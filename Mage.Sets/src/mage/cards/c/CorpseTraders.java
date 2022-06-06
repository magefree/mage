
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 * @author noxx
 */
public final class CorpseTraders extends CardImpl {

    public CorpseTraders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}{B}, Sacrifice a creature: Target opponent reveals their hand. You choose a card from it. That player discards that card. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new DiscardCardYouChooseTargetEffect(), new ManaCostsImpl<>("{2}{B}"));
        ability.addTarget(new TargetOpponent());
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        this.addAbility(ability);
    }

    private CorpseTraders(final CorpseTraders card) {
        super(card);
    }

    @Override
    public CorpseTraders copy() {
        return new CorpseTraders(this);
    }
}
