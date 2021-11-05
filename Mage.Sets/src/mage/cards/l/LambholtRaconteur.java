package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LambholtRaconteur extends CardImpl {

    public LambholtRaconteur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);
        this.secondSideCardClazz = mage.cards.l.LambholtRavager.class;

        // Whenever you cast a noncreature spell, Lambholt Raconteur deals 1 damage to each opponent.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DamagePlayersEffect(1, TargetController.OPPONENT),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));

        // Daybound
        this.addAbility(new TransformAbility());
        this.addAbility(new DayboundAbility());
    }

    private LambholtRaconteur(final LambholtRaconteur card) {
        super(card);
    }

    @Override
    public LambholtRaconteur copy() {
        return new LambholtRaconteur(this);
    }
}
