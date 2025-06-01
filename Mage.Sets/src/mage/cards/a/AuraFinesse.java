package mage.cards.a;

import mage.abilities.effects.common.AttachTargetToTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class AuraFinesse extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.AURA);

    public AuraFinesse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Attach target Aura you control to target creature.
        this.getSpellAbility().addEffect(new AttachTargetToTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private AuraFinesse(final AuraFinesse card) {
        super(card);
    }

    @Override
    public AuraFinesse copy() {
        return new AuraFinesse(this);
    }
}
