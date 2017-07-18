package mage.cards.a;

import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

public class ApocalypseDemon extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("nother creature");
    
    static {       
        filter.add(new CardTypePredicate(CardType.CREATURE));        
        filter.add(new AnotherPredicate());
    }

    public ApocalypseDemon(UUID ownerId, CardSetInfo cardSetInfo) {
        super(ownerId, cardSetInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        subtype.add(SubType.DEMON);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Apocalypse Demon’s power and toughness are each equal to the number of cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(new CardsInControllerGraveyardCount(), Duration.EndOfGame)));

        // At the beginning of your upkeep, tap Apocalypse Demon unless you sacrifice another creature.
        TapSourceUnlessPaysEffect tapEffect = new TapSourceUnlessPaysEffect(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        tapEffect.setText("At the beginning of your upkeep, tap Apocalypse Demon unless you sacrifice another creature.");
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(tapEffect, TargetController.YOU, false));
    }   
           
    public ApocalypseDemon(final ApocalypseDemon apocalypseDemon) {
        super(apocalypseDemon);
    }

    public ApocalypseDemon copy() {
        return new ApocalypseDemon(this);
    }
}
