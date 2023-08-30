package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class HeartflameDuelist extends AdventureCard {

    private static final FilterCard filter = new FilterCard("instant and sorcery spells you control");

    static {
        filter.add(Predicates.or(CardType.INSTANT.getPredicate(), CardType.SORCERY.getPredicate()));
    }

    public HeartflameDuelist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{1}{W}", "Heartflame Slash", "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Instant and sorcery spells you control have lifelink.
        Effect effect = new GainAbilityControlledSpellsEffect(LifelinkAbility.getInstance(), filter);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // Heartflame Slash
        // Heartflame Slash deals 3 damage to any target.
        this.getSpellCard().getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellCard().getSpellAbility().addTarget(new TargetAnyTarget());

        this.finalizeAdventure();
    }

    private HeartflameDuelist(final HeartflameDuelist card) {
        super(card);
    }

    @Override
    public HeartflameDuelist copy() {
        return new HeartflameDuelist(this);
    }
}
