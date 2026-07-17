package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class HonorboundPage extends PrepareCard {

    public HonorboundPage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}", "Forum's Favor", new CardType[]{CardType.SORCERY}, "{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // Forum's Favor
        // Sorcery {W}
        // Target creature gets +1/+0 and gains flying until end of turn.
        this.getSpellCard().getSpellAbility().addEffect(new BoostTargetEffect(1, 0)
            .setText("target creature gets +1/+0"));
        this.getSpellCard().getSpellAbility().addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance())
            .setText("and gains flying until end of turn"));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private HonorboundPage(final HonorboundPage card) {
        super(card);
    }

    @Override
    public HonorboundPage copy() {
        return new HonorboundPage(this);
    }
}
