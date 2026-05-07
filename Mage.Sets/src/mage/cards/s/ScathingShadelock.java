package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.BecomePreparedSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ScathingShadelock extends PrepareCard {

    public ScathingShadelock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}", "Venomous Words", new CardType[]{CardType.SORCERY}, "{B}");

        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // At the beginning of your first main phase, this creature becomes prepared.
        this.addAbility(new BeginningOfFirstMainTriggeredAbility(new BecomePreparedSourceEffect()));

        // Venomous Words
        // Sorcery {B}
        // Target creature you control gets +2/+0 and gains deathtouch until end of turn.
        this.getSpellCard().getSpellAbility().addEffect(
            new BoostTargetEffect(2, 0).setText("Target creature you control gets +2/+0")
        );
        this.getSpellCard().getSpellAbility().addEffect(
            new GainAbilityTargetEffect(DeathtouchAbility.getInstance()).setText("and gains deathtouch until end of turn")
        );
        this.getSpellCard().getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private ScathingShadelock(final ScathingShadelock card) {
        super(card);
    }

    @Override
    public ScathingShadelock copy() {
        return new ScathingShadelock(this);
    }
}
