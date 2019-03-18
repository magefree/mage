

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class BloodSpeaker extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new  FilterControlledCreaturePermanent("a Demon under your control");
    private static final FilterCard filterCard = new  FilterCard("Demon card");
    static {
        filter.add(new SubtypePredicate(SubType.DEMON));
        filterCard.add(new SubtypePredicate(SubType.DEMON));
    }

    public BloodSpeaker (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.OGRE, SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // At the beginning of your upkeep, you may sacrifice Blood Speaker. If you do, search your library for a Demon card, reveal that card, and put it into your hand. Then shuffle your library.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new SacrificeSourceEffect(), TargetController.YOU, true);
        ability.addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filterCard), true, true, "If you do, search your library for "));
        this.addAbility(ability);

        // Whenever a Demon enters the battlefield under your control, return Blood Speaker from your graveyard to your hand.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), filter, false));
    }

    public BloodSpeaker (final BloodSpeaker card) {
        super(card);
    }

    @Override
    public BloodSpeaker copy() {
        return new BloodSpeaker(this);
    }

}
