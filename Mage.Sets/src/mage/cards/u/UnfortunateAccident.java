package mage.cards.u;

import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.SpreeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.MercenaryToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnfortunateAccident extends CardImpl {

    public UnfortunateAccident(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Spree
        this.addAbility(new SpreeAbility(this));

        // + {2}{B} -- Destroy target creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().withFirstModeCost(new ManaCostsImpl<>("{2}{B}"));

        // + {1} -- Create a 1/1 red Mercenary creature token with "{T}: Target creature you control gets +1/+0 until end of turn. Activate only as a sorcery."
        this.getSpellAbility().addMode(new Mode(new CreateTokenEffect(new MercenaryToken()))
                .withCost(new GenericManaCost(1)));
    }

    private UnfortunateAccident(final UnfortunateAccident card) {
        super(card);
    }

    @Override
    public UnfortunateAccident copy() {
        return new UnfortunateAccident(this);
    }
}
