

package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki, North
 */
public final class Painsmith extends CardImpl {
    public Painsmith (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        SpellCastControllerTriggeredAbility ability = new SpellCastControllerTriggeredAbility(new BoostTargetEffect(2, 0, Duration.EndOfTurn), StaticFilters.FILTER_SPELL_AN_ARTIFACT, true);
        ability.addEffect(new GainAbilityTargetEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public Painsmith (final Painsmith card) {
        super(card);
    }

    @Override
    public Painsmith copy() {
        return new Painsmith(this);
    }
}
