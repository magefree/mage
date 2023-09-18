package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DeathcultRogue extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("except by Rogues");

    static {
        filter.add(Predicates.not(SubType.ROGUE.getPredicate()));
    }

    public DeathcultRogue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U/B}{U/B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Deathcult Rogue can't be blocked except by Rogues.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));

    }

    private DeathcultRogue(final DeathcultRogue card) {
        super(card);
    }

    @Override
    public DeathcultRogue copy() {
        return new DeathcultRogue(this);
    }
}
