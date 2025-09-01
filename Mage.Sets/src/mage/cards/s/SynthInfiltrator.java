package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.keyword.ImproviseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class SynthInfiltrator extends CardImpl {

    public SynthInfiltrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.SYNTH);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Improvise
        this.addAbility(new ImproviseAbility());

        // You may have Synth Infiltrator enter the battlefield as a copy of any creature on the battlefield, except it's a Synth artifact creature in addition to its other types.
        CopyApplier synthInfiltratorCopyApplier = new CopyApplier() {
            @Override
            public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
                blueprint.addCardType(CardType.ARTIFACT);
                blueprint.addCardType(CardType.CREATURE);
                blueprint.addSubType(SubType.SYNTH);
                return true;
            }
        };
        Effect effect = new CopyPermanentEffect(StaticFilters.FILTER_PERMANENT_CREATURE, synthInfiltratorCopyApplier);
        effect.setText("You may have {this} enter as a copy of any creature on the battlefield, except it's a Synth artifact creature in addition to its other types");
        Ability ability = new SimpleStaticAbility(Zone.ALL, new EntersBattlefieldEffect(effect, "", true));
        this.addAbility(ability);
    }

    private SynthInfiltrator(final SynthInfiltrator card) {
        super(card);
    }

    @Override
    public SynthInfiltrator copy() {
        return new SynthInfiltrator(this);
    }
}
