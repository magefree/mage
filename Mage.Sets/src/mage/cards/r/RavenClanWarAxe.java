package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RavenClanWarAxe extends CardImpl {

    private static final FilterCard filter = new FilterCard("Eivor, Battle-Ready");

    static {
        filter.add(new NamePredicate("Eivor, Battle-Ready"));
    }

    public RavenClanWarAxe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Raven Clan War-Axe enters the battlefield, you may search your library and/or graveyard for a card named Eivor, Battle-Ready, reveal it, and put it into your hand. If you search your library this way, shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryGraveyardPutInHandEffect(filter)));

        // Equipped creature gets +2/+0 and has trample.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 0));
        ability.addEffect(new GainAbilityAttachedEffect(
                TrampleAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has trample"));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private RavenClanWarAxe(final RavenClanWarAxe card) {
        super(card);
    }

    @Override
    public RavenClanWarAxe copy() {
        return new RavenClanWarAxe(this);
    }
}
