package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainProtectionFromColorTargetEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class SyggRiverGuide extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.MERFOLK, "Merfolk you control");

    public SyggRiverGuide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Islandwalk
        this.addAbility(new IslandwalkAbility());
        // {1}{W}: Target Merfolk you control gains protection from the color of your choice until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainProtectionFromColorTargetEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{1}{W}"));
        ability.addTarget(new TargetControlledPermanent(filter));
        this.addAbility(ability);

    }

    private SyggRiverGuide(final SyggRiverGuide card) {
        super(card);
    }

    @Override
    public SyggRiverGuide copy() {
        return new SyggRiverGuide(this);
    }
}
