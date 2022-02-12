package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class RustScarab extends CardImpl {

    private static final FilterPermanent filter
            = new FilterArtifactOrEnchantmentPermanent("artifact or enchantment defending player controls");

    static {
        filter.add(DefendingPlayerControlsPredicate.instance);
    }

    public RustScarab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Whenever Rust Scarab becomes blocked, you may destroy target artifact or enchantment defending player controls.
        Ability ability = new BecomesBlockedSourceTriggeredAbility(new DestroyTargetEffect(), true);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private RustScarab(final RustScarab card) {
        super(card);
    }

    @Override
    public RustScarab copy() {
        return new RustScarab(this);
    }
}
