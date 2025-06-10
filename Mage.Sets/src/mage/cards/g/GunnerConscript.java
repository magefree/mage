package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttachedToSourcePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.JunkToken;

import java.util.Objects;
import java.util.UUID;

/**
 * @author notgreat
 */
public final class GunnerConscript extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Aura and Equipment attached to it");

    static {
        filter.add(Predicates.or(
                SubType.AURA.getPredicate(),
                SubType.EQUIPMENT.getPredicate()
        ));
        filter.add(AttachedToSourcePredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public GunnerConscript(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Gunner Conscript gets +1/+1 for each Aura and Equipment attached to it.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(xValue, xValue, Duration.WhileOnBattlefield)));

        // When Gunner Conscript dies, if it was enchanted, create a Junk token.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new JunkToken()))
                .withInterveningIf(GunnerConscriptEnchantedCondition.instance));

        // When Gunner Conscript dies, if it was equipped, create a Junk token.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new JunkToken()))
                .withInterveningIf(GunnerConscriptEquippedCondition.instance));
    }

    private GunnerConscript(final GunnerConscript card) {
        super(card);
    }

    @Override
    public GunnerConscript copy() {
        return new GunnerConscript(this);
    }
}


// Derived from KollTheForgemaster
// Custom predicates call getPermanentOrLKIBattlefield (needed for the death trigger to work correctly)
enum GunnerConscriptEnchantedCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = source.getSourcePermanentOrLKI(game);
        return sourcePermanent != null && sourcePermanent.getAttachments()
                .stream()
                .map(game::getPermanentOrLKIBattlefield)
                .filter(Objects::nonNull)
                .anyMatch(permanent -> permanent.isEnchantment(game));
    }

    @Override
    public String toString() {
        return "it was enchanted";
    }
}

enum GunnerConscriptEquippedCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = source.getSourcePermanentOrLKI(game);
        return sourcePermanent != null && sourcePermanent.getAttachments()
                .stream()
                .map(game::getPermanentOrLKIBattlefield)
                .filter(Objects::nonNull)
                .anyMatch(attachment -> attachment.hasSubtype(SubType.EQUIPMENT, game));
    }

    @Override
    public String toString() {
        return "it was equipped";
    }
}
