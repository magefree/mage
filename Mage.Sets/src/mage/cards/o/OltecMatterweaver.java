package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.GnomeToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OltecMatterweaver extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledArtifactPermanent("artifact token you control");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public OltecMatterweaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever you cast a creature spell, choose one --
        // * Create a 1/1 colorless Gnome artifact creature token.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new GnomeToken()), StaticFilters.FILTER_SPELL_A_CREATURE, false
        );

        // * Create a token that's a copy of target artifact token you control.
        ability.addMode(new Mode(new CreateTokenCopyTargetEffect()).addTarget(new TargetPermanent(filter)));
        this.addAbility(ability);
    }

    private OltecMatterweaver(final OltecMatterweaver card) {
        super(card);
    }

    @Override
    public OltecMatterweaver copy() {
        return new OltecMatterweaver(this);
    }
}
