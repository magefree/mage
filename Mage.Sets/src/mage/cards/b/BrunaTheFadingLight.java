package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class BrunaTheFadingLight extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("Angel or Human creature card");

    static {
        filter.add(Predicates.or(
                SubType.HUMAN.getPredicate(),
                SubType.ANGEL.getPredicate()
        ));
    }

    public BrunaTheFadingLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL, SubType.HORROR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        this.meldsWithClazz = mage.cards.g.GiselaTheBrokenBlade.class;
        this.meldsToClazz = mage.cards.b.BriselaVoiceOfNightmares.class;

        // When you cast Bruna, the Fading Light, you may return target Angel or Human creature card from your graveyard to the battlefield.
        Effect effect = new ReturnFromGraveyardToBattlefieldTargetEffect();
        effect.setText("you may return target Angel or Human creature card from your graveyard to the battlefield");
        Ability ability = new CastSourceTriggeredAbility(effect, true);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // <i>(Melds with Gisela, the Broken Blade.)</i>
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("<i>(Melds with Gisela, the Broken Blade.)</i>")));
    }

    private BrunaTheFadingLight(final BrunaTheFadingLight card) {
        super(card);
    }

    @Override
    public BrunaTheFadingLight copy() {
        return new BrunaTheFadingLight(this);
    }
}
