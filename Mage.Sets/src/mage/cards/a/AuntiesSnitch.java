package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.condition.common.SourceInGraveyardCondition;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.keyword.ProwlAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class AuntiesSnitch extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Goblin or Rogue you control");
    static {
        filter.add(Predicates.or(SubType.GOBLIN.getPredicate(), SubType.ROGUE.getPredicate()));
    }

    public AuntiesSnitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Auntie's Snitch can't block.
        this.addAbility(new CantBlockAbility());

        // Prowl {1}{B}
        this.addAbility(new ProwlAbility("{1}{B}"));

        // Whenever a Goblin or Rogue you control deals combat damage to a player, if Auntie's Snitch is in your graveyard, you may return Auntie's Snitch to your hand.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToHandEffect().setText("return this card to your hand"),
                filter, true, SetTargetPointer.NONE, true, false
                ).withInterveningIf(SourceInGraveyardCondition.instance));
    }

    private AuntiesSnitch(final AuntiesSnitch card) {
        super(card);
    }

    @Override
    public AuntiesSnitch copy() {
        return new AuntiesSnitch(this);
    }
}
