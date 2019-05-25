package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.PersistAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LesserMasticore extends CardImpl {

    public LesserMasticore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.MASTICORE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As an additional cost to cast this spell, discard a card.
        this.getSpellAbility().addCost(new DiscardCardCost());

        // {4}: Lesser Masticore deals 1 damage to target creature.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(1), new GenericManaCost(4)
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Persist
        this.addAbility(new PersistAbility());
    }

    private LesserMasticore(final LesserMasticore card) {
        super(card);
    }

    @Override
    public LesserMasticore copy() {
        return new LesserMasticore(this);
    }
}
