package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.common.EntersBattlefieldUntappedTriggeredAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MysticSanctuary extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.ISLAND, "other Islands");
    private static final FilterCard filter2
            = new FilterInstantOrSorceryCard("instant or sorcery card from your graveyard");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final YouControlPermanentCondition condition
            = new YouControlPermanentCondition(filter, ComparisonType.OR_GREATER, 3);

    public MysticSanctuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.ISLAND);

        // ({T}: Add {U}.)
        this.addAbility(new BlueManaAbility());

        // Mystic Sanctuary enters the battlefield tapped unless you control three or more other Islands.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // When Mystic Sanctuary enters the battlefield untapped, you may put target instant or sorcery card from your graveyard on top of your library.
        Ability ability = new EntersBattlefieldUntappedTriggeredAbility(
                new PutOnLibraryTargetEffect(true)
                        .setText("put target instant or sorcery card from your graveyard on top of your library"),
                true
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter2));
        this.addAbility(ability);
    }

    private MysticSanctuary(final MysticSanctuary card) {
        super(card);
    }

    @Override
    public MysticSanctuary copy() {
        return new MysticSanctuary(this);
    }
}
