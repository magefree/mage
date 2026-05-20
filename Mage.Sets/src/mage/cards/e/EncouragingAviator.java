package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.BecomePreparedSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EncouragingAviator extends PrepareCard {

    public EncouragingAviator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}", "Jump", new CardType[]{CardType.INSTANT}, "{U}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever this creature attacks, it becomes prepared.
        this.addAbility(new AttacksTriggeredAbility(new BecomePreparedSourceEffect()));

        // Jump
        // Instant {U}
        // Target creature gains flying until end of turn.
        this.getSpellCard().getSpellAbility().addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance()));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private EncouragingAviator(final EncouragingAviator card) {
        super(card);
    }

    @Override
    public EncouragingAviator copy() {
        return new EncouragingAviator(this);
    }
}
