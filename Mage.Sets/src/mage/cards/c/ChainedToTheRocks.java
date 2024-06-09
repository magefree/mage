package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 * @author awjackson
 */
public final class ChainedToTheRocks extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledLandPermanent("Mountain you control");

    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
    }

    public ChainedToTheRocks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}");
        this.subtype.add(SubType.AURA);

        // Enchant Mountain you control
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Exile));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When Chained to the Rocks enters the battlefield, exile target creature an opponent controls until Chained to the Rocks leaves the battlefield. (That creature returns under its owner's control.)
        ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private ChainedToTheRocks(final ChainedToTheRocks card) {
        super(card);
    }

    @Override
    public ChainedToTheRocks copy() {
        return new ChainedToTheRocks(this);
    }
}
