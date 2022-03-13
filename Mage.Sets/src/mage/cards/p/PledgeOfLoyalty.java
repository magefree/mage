package mage.cards.p;

import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author noahg
 */
public final class PledgeOfLoyalty extends CardImpl {

    private static final FilterCard filter = new FilterCard("the colors of permanents you control");

    static {
        filter.add(PledgeOfLoyaltyPredicate.instance);
    }

    public PledgeOfLoyalty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Enchanted creature has protection from the colors of permanents you control. This effect doesn't remove Pledge of Loyalty.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                new ProtectionAbility(filter), AttachmentType.AURA
        ).setDoesntRemoveItself(true)));
    }

    private PledgeOfLoyalty(final PledgeOfLoyalty card) {
        super(card);
    }

    @Override
    public PledgeOfLoyalty copy() {
        return new PledgeOfLoyalty(this);
    }
}

enum PledgeOfLoyaltyPredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        ObjectColor color = input.getObject().getColor(game);
        return color.hasColor()
                && game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT,
                        input.getPlayerId(), input.getSource(), game
                ).stream()
                .anyMatch(permanent -> permanent.getColor(game).shares(color));
    }
}
