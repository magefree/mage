package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class LoathsomeCatoblepas extends CardImpl {

    public LoathsomeCatoblepas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}{G}: Loathsome Catoblepas must be blocked this turn if able.
        this.addAbility(new SimpleActivatedAbility(
                new MustBeBlockedByAtLeastOneSourceEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{2}{G}")
        ));

        // When Loathsome Catoblepas dies, target creature an opponent controls gets -3/-3 until end of turn.
        Ability ability = new DiesSourceTriggeredAbility(new BoostTargetEffect(-3, -3));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private LoathsomeCatoblepas(final LoathsomeCatoblepas card) {
        super(card);
    }

    @Override
    public LoathsomeCatoblepas copy() {
        return new LoathsomeCatoblepas(this);
    }
}
