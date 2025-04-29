package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.OmenCard;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterBasicLandCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Jmlundeen
 */
public final class SaguWildling extends OmenCard {

    public SaguWildling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE},  new CardType[]{CardType.SORCERY}, "{4}{G}", "Roost Seek", "{G}");
        
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, you gain 3 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3)));

        // Roost Seek
        // Search your library for a basic land card, reveal it, put it into your hand, then shuffle.
        TargetCardInLibrary target = new TargetCardInLibrary(new FilterBasicLandCard());
        this.getSpellCard().getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(target, true));
        this.finalizeOmen();
    }

    private SaguWildling(final SaguWildling card) {
        super(card);
    }

    @Override
    public SaguWildling copy() {
        return new SaguWildling(this);
    }
}
