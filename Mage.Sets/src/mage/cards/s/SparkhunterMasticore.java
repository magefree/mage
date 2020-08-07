package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterObject;
import mage.target.common.TargetPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SparkhunterMasticore extends CardImpl {

    private static final FilterObject filter = new FilterObject("planeswalkers");

    static {
        filter.add(CardType.PLANESWALKER.getPredicate());
    }

    public SparkhunterMasticore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.MASTICORE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // As an additional cost to cast this spell, discard a card.
        this.getSpellAbility().addCost(new DiscardCardCost());

        // Protection from planeswalkers
        this.addAbility(new ProtectionAbility(filter));

        // {1}: Sparkhunter Masticore deals 1 damage to target planeswalker.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(1), new GenericManaCost(1));
        ability.addTarget(new TargetPlaneswalkerPermanent());
        this.addAbility(ability);

        // {3}: Sparkhunter Masticore gains indestructible until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ), new GenericManaCost(3)));
    }

    private SparkhunterMasticore(final SparkhunterMasticore card) {
        super(card);
    }

    @Override
    public SparkhunterMasticore copy() {
        return new SparkhunterMasticore(this);
    }
}
