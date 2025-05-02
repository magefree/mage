package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.search.SearchLibraryPutOntoBattlefieldTappedRestInHandEffect;
import mage.cards.OmenCard;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterBasicLandCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.Target;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Jmlundeen
 */
public final class BloomvineRegent extends OmenCard {

    private static final FilterCard filter = new FilterCard("basic Forest cards");

    static {
        filter.add(SubType.FOREST.getPredicate());
        filter.add(SuperType.BASIC.getPredicate());
    }

    public BloomvineRegent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{3}{G}{G}", "Claim Territory", "{2}{G}");
        
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever this creature or another Dragon you control enters, you gain 3 life.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new GainLifeEffect(3),
                new FilterCreaturePermanent(SubType.DRAGON, "Dragon"),
                false,
                true
        ));

        // Claim Territory
        // Search your library for up to two basic Forest cards, reveal them, put one onto the battlefield tapped and the other into your hand, then shuffle. (Also shuffle this card.)
        TargetCardInLibrary target = new TargetCardInLibrary(0, 2, filter);
        this.getSpellCard().getSpellAbility().addEffect(new SearchLibraryPutOntoBattlefieldTappedRestInHandEffect(target, 1));
        this.finalizeOmen();
    }

    private BloomvineRegent(final BloomvineRegent card) {
        super(card);
    }

    @Override
    public BloomvineRegent copy() {
        return new BloomvineRegent(this);
    }
}
