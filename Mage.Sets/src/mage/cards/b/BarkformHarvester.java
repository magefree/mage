package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BarkformHarvester extends CardImpl {

    public BarkformHarvester(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Changeling
        this.addAbility(new ChangelingAbility());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // {2}: Put target card from your graveyard on the bottom of your library.
        Ability ability = new SimpleActivatedAbility(new PutOnLibraryTargetEffect(false), new GenericManaCost(2));
        ability.addTarget(new TargetCardInYourGraveyard());
        this.addAbility(ability);
    }

    private BarkformHarvester(final BarkformHarvester card) {
        super(card);
    }

    @Override
    public BarkformHarvester copy() {
        return new BarkformHarvester(this);
    }
}
