
package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.UnblockedPredicate;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetadjustment.DamagedPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class Skullsnatcher extends CardImpl {

    private static final FilterCard filterGraveyardCard = new FilterCard("cards from that player's graveyard");
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("unblocked attacker you control");

    static {
        filter.add(UnblockedPredicate.instance);
    }

    public Skullsnatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.NINJA);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Ninjutsu {B} ({B}, Return an unblocked attacker you control to hand: Put this card onto the battlefield from your hand tapped and attacking.)
        this.addAbility(new NinjutsuAbility("{B}"));

        // Whenever Skullsnatcher deals combat damage to a player, exile up to two target cards from that player's graveyard.
        Effect effect = new ExileTargetEffect(null, "", Zone.GRAVEYARD);
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(effect, false, true);
        ability.addTarget(new TargetCardInGraveyard(0, 2, filterGraveyardCard));
        ability.setTargetAdjuster(new DamagedPlayerControlsTargetAdjuster(true));
        this.addAbility(ability);
    }

    private Skullsnatcher(final Skullsnatcher card) {
        super(card);
    }

    @Override
    public Skullsnatcher copy() {
        return new Skullsnatcher(this);
    }
}
