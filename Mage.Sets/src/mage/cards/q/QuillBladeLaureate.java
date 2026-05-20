package mage.cards.q;

import mage.MageInt;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class QuillBladeLaureate extends PrepareCard {

    public QuillBladeLaureate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}", "Twofold Intent", new CardType[]{CardType.SORCERY}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // Twofold Intent
        // Sorcery {1}{W}
        // Target creature gets +1/+0 and gains double strike until end of turn.
        this.getSpellCard().getSpellAbility().addEffect(new BoostTargetEffect(1, 0)
                .setText("target creature gets +1/+0"));
        this.getSpellCard().getSpellAbility().addEffect(new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance())
                .setText("and gains double strike until end of turn"));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private QuillBladeLaureate(final QuillBladeLaureate card) {
        super(card);
    }

    @Override
    public QuillBladeLaureate copy() {
        return new QuillBladeLaureate(this);
    }
}
