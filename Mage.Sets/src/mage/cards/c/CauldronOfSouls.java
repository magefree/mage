package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.PersistAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class CauldronOfSouls extends CardImpl {

    public CauldronOfSouls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // {tap}: Choose any number of target creatures. Each of those creatures gains persist until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilityTargetEffect(new PersistAbility(), Duration.EndOfTurn)
                        .setText("choose any number of target creatures. Each of those creatures gains persist until end of turn"),
                new TapSourceCost()
        );
        ability.addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE));
        this.addAbility(ability);
    }

    private CauldronOfSouls(final CauldronOfSouls card) {
        super(card);
    }

    @Override
    public CauldronOfSouls copy() {
        return new CauldronOfSouls(this);
    }
}
