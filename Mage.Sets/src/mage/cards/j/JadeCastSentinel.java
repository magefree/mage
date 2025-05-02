package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JadeCastSentinel extends CardImpl {

    public JadeCastSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.APE);
        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // {2}, {T}: Put target card from a graveyard on the bottom of its owner's library.
        Ability ability = new SimpleActivatedAbility(new PutOnLibraryTargetEffect(false), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);
    }

    private JadeCastSentinel(final JadeCastSentinel card) {
        super(card);
    }

    @Override
    public JadeCastSentinel copy() {
        return new JadeCastSentinel(this);
    }
}
