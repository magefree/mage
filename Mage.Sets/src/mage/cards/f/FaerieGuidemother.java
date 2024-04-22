package mage.cards.f;

import mage.MageInt;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FaerieGuidemother extends AdventureCard {

    public FaerieGuidemother(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{W}", "Gift of the Fae", "{1}{W}");

        this.subtype.add(SubType.FAERIE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Gift of the Fae
        // Target creature gets +2/+1 and gains flying until end of turn.
        this.getSpellCard().getSpellAbility().addEffect(new BoostTargetEffect(
                2, 1, Duration.EndOfTurn
        ).setText("Target creature gets +2/+1"));
        this.getSpellCard().getSpellAbility().addEffect(new GainAbilityTargetEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains flying until end of turn"));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        this.finalizeAdventure();
    }

    private FaerieGuidemother(final FaerieGuidemother card) {
        super(card);
    }

    @Override
    public FaerieGuidemother copy() {
        return new FaerieGuidemother(this);
    }
}
