package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.keyword.CleaveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LanternFlare extends CardImpl {

    public LanternFlare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Cleave {X}{R}{W}
        Ability ability = new CleaveAbility(
                this, new DamageTargetEffect(ManacostVariableValue.REGULAR), "{X}{R}{W}"
        );
        ability.addEffect(new GainLifeEffect(ManacostVariableValue.REGULAR));
        ability.addTarget(new TargetCreatureOrPlaneswalker());
        this.addAbility(ability);

        // Lantern Flare deals X damage to target creature or planeswalker and you gain X life. [X is the number of creatures you control.]
        this.getSpellAbility().addEffect(new DamageTargetEffect(
                CreaturesYouControlCount.instance
        ).setText("{this} deals X damage to target creature or planeswalker"));
        this.getSpellAbility().addEffect(new GainLifeEffect(
                CreaturesYouControlCount.instance,
                "and you gain X life. [X is the number of creatures you control.]"
        ));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
        this.getSpellAbility().addHint(CreaturesYouControlHint.instance);
    }

    private LanternFlare(final LanternFlare card) {
        super(card);
    }

    @Override
    public LanternFlare copy() {
        return new LanternFlare(this);
    }
}
