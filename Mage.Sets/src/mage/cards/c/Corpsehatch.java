package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.EldraziSpawnToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class Corpsehatch extends CardImpl {

    public Corpsehatch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}{B}");

        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new CreateTokenEffect(new EldraziSpawnToken(), 2));
    }

    private Corpsehatch(final Corpsehatch card) {
        super(card);
    }

    @Override
    public Corpsehatch copy() {
        return new Corpsehatch(this);
    }
}
