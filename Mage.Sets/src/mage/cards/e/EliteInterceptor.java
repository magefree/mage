package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.MayTapOrUntapTargetEffect;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class EliteInterceptor extends PrepareCard {

    public EliteInterceptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}", "Rejoinder", new CardType[]{CardType.SORCERY}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // Rejoinder
        // Sorcery {1}{W}
        // You may tap or untap target creature.
        // Draw a card.
        this.getSpellCard().getSpellAbility().addEffect(new MayTapOrUntapTargetEffect());
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellCard().getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private EliteInterceptor(final EliteInterceptor card) {
        super(card);
    }

    @Override
    public EliteInterceptor copy() {
        return new EliteInterceptor(this);
    }
}
