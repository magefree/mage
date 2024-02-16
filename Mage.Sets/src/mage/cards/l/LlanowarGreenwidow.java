package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.costadjusters.DomainAdjuster;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.replacement.LeaveBattlefieldExileSourceReplacementEffect;
import mage.abilities.hint.common.DomainHint;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 *
 * @author weirddan455
 */
public final class LlanowarGreenwidow extends CardImpl {

    public LlanowarGreenwidow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Domain -- {7}{G}: Return Llanowar Greenwidow from your graveyard to the battlefield tapped. It gains "If this permanent would leave the battlefield, exile it instead of putting it anywhere else." This ability costs {1} less to activate for each basic land type among lands you control.
        Ability ability = new SimpleActivatedAbility(
                Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToBattlefieldEffect(true, false),
                new ManaCostsImpl<>("{7}{G}")
        );
        ability.addEffect(new GainAbilitySourceEffect(
                new SimpleStaticAbility(new LeaveBattlefieldExileSourceReplacementEffect("this permanent")),
                Duration.Custom
        ).setText("It gains \"If this permanent would leave the battlefield, exile it instead of putting it anywhere else.\""));
        ability.addEffect(new InfoEffect("This ability costs {1} less to activate " +
                "for each basic land type among lands you control."));
        ability.addHint(DomainHint.instance);
        ability.setAbilityWord(AbilityWord.DOMAIN);
        ability.setCostAdjuster(DomainAdjuster.instance);
        this.addAbility(ability);
    }

    private LlanowarGreenwidow(final LlanowarGreenwidow card) {
        super(card);
    }

    @Override
    public LlanowarGreenwidow copy() {
        return new LlanowarGreenwidow(this);
    }
}
