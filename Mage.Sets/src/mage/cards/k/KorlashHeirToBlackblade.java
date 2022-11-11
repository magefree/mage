
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.abilityword.GrandeurAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author emerald000
 */
public final class KorlashHeirToBlackblade extends CardImpl {
    
    private static final FilterControlledPermanent filterPermanent = new FilterControlledPermanent("Swamps you control");
    private static final FilterCard filterCard = new FilterLandCard("Swamp cards");
    static {
        filterPermanent.add(SubType.SWAMP.getPredicate());
        filterCard.add(SubType.SWAMP.getPredicate());
    }

    public KorlashHeirToBlackblade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Korlash, Heir to Blackblade's power and toughness are each equal to the number of Swamps you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filterPermanent), Duration.EndOfGame)));

        // {1}{B}: Regenerate Korlash.
        Effect effect = new RegenerateSourceEffect();
        effect.setText("Regenerate Korlash.");
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{1}{B}")));
        
        // Grandeur - Discard another card named Korlash, Heir to Blackblade: Search your library for up to two Swamp cards, put them onto the battlefield tapped, then shuffle your library.
        effect = new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 2, filterCard), true, true);
        effect.setText("Search your library for up to two Swamp cards, put them onto the battlefield tapped, then shuffle.");
        this.addAbility(new GrandeurAbility(effect, "Korlash, Heir to Blackblade"));
    }

    private KorlashHeirToBlackblade(final KorlashHeirToBlackblade card) {
        super(card);
    }

    @Override
    public KorlashHeirToBlackblade copy() {
        return new KorlashHeirToBlackblade(this);
    }
}
