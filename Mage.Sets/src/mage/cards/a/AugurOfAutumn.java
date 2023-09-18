package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CovenCondition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayTheTopCardEffect;
import mage.abilities.hint.common.CovenHint;
import mage.constants.AbilityWord;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterLandCard;

/**
 *
 * @author weirddan455
 */
public final class AugurOfAutumn extends CardImpl {

    private static final FilterCard filter = new FilterLandCard("play lands");
    private static final FilterCard filter2 = new FilterCreatureCard("cast creature spells");

    public AugurOfAutumn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // You may play lands from the top of your library.
        this.addAbility(new SimpleStaticAbility(new PlayTheTopCardEffect(TargetController.YOU, filter, false)));

        // Coven — As long as you control three or more creatures with different powers, you may cast creature spells from the top of your library.
        Effect effect = new ConditionalAsThoughEffect(
                new PlayTheTopCardEffect(TargetController.YOU, filter2, false),
                CovenCondition.instance
        );
        effect.setText("As long as you control three or more creatures with different powers, you may cast creature spells from the top of your library");
        Ability ability = new SimpleStaticAbility(effect);
        ability.setAbilityWord(AbilityWord.COVEN);
        ability.addHint(CovenHint.instance);
        this.addAbility(ability);
    }

    private AugurOfAutumn(final AugurOfAutumn card) {
        super(card);
    }

    @Override
    public AugurOfAutumn copy() {
        return new AugurOfAutumn(this);
    }
}
