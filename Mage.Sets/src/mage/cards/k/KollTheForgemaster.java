package mage.cards.k;

import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.EnchantedPredicate;
import mage.filter.predicate.permanent.EquippedPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author weirddan455
 */
public final class KollTheForgemaster extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("another nontoken creature you control");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent();

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
        filter.add(Predicates.or(KollTheForgemasterEnchantedPredicate.instance, KollTheForgemasterEquippedPredicate.instance));
        filter2.add(TokenPredicate.TRUE);
        filter2.add(Predicates.or(EnchantedPredicate.instance, EquippedPredicate.instance));
    }

    public KollTheForgemaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever another nontoken creature you control dies, if it was enchanted or equipped, return it to its owner's hand.
        this.addAbility(new DiesCreatureTriggeredAbility(new ReturnToHandTargetEffect()
                .setText("if it was enchanted or equipped, return it to its owner's hand"), false, filter, true
        ));

        // Creature tokens you control that are enchanted or equipped get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter2)
                .setText("Creature tokens you control that are enchanted or equipped get +1/+1")
        ));
    }

    private KollTheForgemaster(final KollTheForgemaster card) {
        super(card);
    }

    @Override
    public KollTheForgemaster copy() {
        return new KollTheForgemaster(this);
    }
}

// Custom predicates call getPermanentOrLKIBattlefield (needed for the death trigger to work correctly)
enum KollTheForgemasterEnchantedPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getAttachments()
                .stream()
                .map(game::getPermanentOrLKIBattlefield)
                .filter(Objects::nonNull)
                .anyMatch(permanent -> permanent.isEnchantment(game));
    }

    @Override
    public String toString() {
        return "Enchanted";
    }
}

enum KollTheForgemasterEquippedPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getAttachments()
                .stream()
                .map(game::getPermanentOrLKIBattlefield)
                .filter(Objects::nonNull)
                .anyMatch(attachment -> attachment.hasSubtype(SubType.EQUIPMENT, game));
    }

    @Override
    public String toString() {
        return "equipped";
    }
}
