
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ExileFromZoneTargetEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public final class MerrowBonegnawer extends CardImpl {

    private UUID exileId = UUID.randomUUID();

    private static final FilterSpell filter = new FilterSpell("a black spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public MerrowBonegnawer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Target player exiles a card from their graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileFromZoneTargetEffect(Zone.GRAVEYARD, exileId, getIdName(), new FilterCard()), new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Whenever you cast a black spell, you may untap Merrow Bonegnawer.
        this.addAbility(new SpellCastControllerTriggeredAbility(Zone.BATTLEFIELD, new UntapSourceEffect(), filter, true, false));

    }

    public MerrowBonegnawer(final MerrowBonegnawer card) {
        super(card);
    }

    @Override
    public MerrowBonegnawer copy() {
        return new MerrowBonegnawer(this);
    }
}
