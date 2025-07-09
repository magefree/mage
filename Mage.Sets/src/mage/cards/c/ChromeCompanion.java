package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChromeCompanion extends CardImpl {

    public ChromeCompanion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.DOG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever this creature becomes tapped, you gain 1 life.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(new GainLifeEffect(1)));

        // {2}, {T}: Put target card from a graveyard on the bottom of its owner's library.
        Ability ability = new SimpleActivatedAbility(new PutOnLibraryTargetEffect(false), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);
    }

    private ChromeCompanion(final ChromeCompanion card) {
        super(card);
    }

    @Override
    public ChromeCompanion copy() {
        return new ChromeCompanion(this);
    }
}
