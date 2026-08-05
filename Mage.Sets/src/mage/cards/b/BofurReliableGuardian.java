package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetControlledPermanent;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class BofurReliableGuardian extends AdventureCard {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifact or creature you control");

    static {
        filter.add(Predicates.or(
            CardType.CREATURE.getPredicate(),
            CardType.ARTIFACT.getPredicate()
        ));
    }

    public BofurReliableGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{W}", "Concerted Care", "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Concerted Care
        // Target artifact or creature you control gains hexproof and indestructible until end of turn.
        this.getSpellCard().getSpellAbility().addEffect(new GainAbilityTargetEffect(HexproofAbility.getInstance())
                .setText("target artifact or creature you control gains hexproof"));
        this.getSpellCard().getSpellAbility().addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance())
                .setText("and indestructible until end of turn"));
        this.getSpellCard().getSpellAbility().addTarget(new TargetControlledPermanent(filter));

        this.finalizeAdventure();
    }

    private BofurReliableGuardian(final BofurReliableGuardian card) {
        super(card);
    }

    @Override
    public BofurReliableGuardian copy() {
        return new BofurReliableGuardian(this);
    }
}
