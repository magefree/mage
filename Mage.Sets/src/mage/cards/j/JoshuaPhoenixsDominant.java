package mage.cards.j;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.ExileSourceAndReturnFaceUpEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.discard.DiscardAndDrawThatManyEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JoshuaPhoenixsDominant extends TransformingDoubleFacedCard {

    public JoshuaPhoenixsDominant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.NOBLE, SubType.WIZARD}, "{1}{R}{W}",
                "Phoenix, Warden of Fire",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.SAGA, SubType.PHOENIX}, "RW");

        // Joshua, Phoenix's Dominant
        this.getLeftHalfCard().setPT(3, 4);

        // When Joshua enters, discard up to two cards, then draw that many cards.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new DiscardAndDrawThatManyEffect(2)));

        // {3}{R}{W}, {T}: Exile Joshua, then return it to the battlefield transformed under its owner's control. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED), new ManaCostsImpl<>("{3}{R}{W}")
        );
        ability.addCost(new TapSourceCost());
        this.getLeftHalfCard().addAbility(ability);

        // Phoenix, Warden of Fire
        this.getRightHalfCard().setPT(4, 4);

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this.getRightHalfCard());

        // I, II -- Rising Flames -- Phoenix deals 2 damage to each opponent.
        sagaAbility.addChapterEffect(this.getRightHalfCard(), SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II, chapter -> {
            chapter.addEffect(new DamagePlayersEffect(2, TargetController.OPPONENT));
            chapter.withFlavorWord("Rising Flames");
        });

        // III -- Flames of Rebirth -- Return any number of target creature cards with total mana value 6 or less from your graveyard to the battlefield. Exile Phoenix, then return it to the battlefield.
        sagaAbility.addChapterEffect(this.getRightHalfCard(), SagaChapter.CHAPTER_III, chapter -> {
            chapter.addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
            chapter.addEffect(new ExileSourceAndReturnFaceUpEffect());
            chapter.addTarget(new PhoenixWardenOfFireTarget());
            chapter.withFlavorWord("Flames of Rebirth");
        });
        this.getRightHalfCard().addAbility(sagaAbility);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.getRightHalfCard().addAbility(LifelinkAbility.getInstance());
    }

    private JoshuaPhoenixsDominant(final JoshuaPhoenixsDominant card) {
        super(card);
    }

    @Override
    public JoshuaPhoenixsDominant copy() {
        return new JoshuaPhoenixsDominant(this);
    }
}

class PhoenixWardenOfFireTarget extends TargetCardInYourGraveyard {

    private static final FilterCard filterStatic = new FilterCreatureCard(
            "creature cards with total mana value 6 or less from your graveyard"
    );

    PhoenixWardenOfFireTarget() {
        super(0, Integer.MAX_VALUE, filterStatic, false);
    }

    private PhoenixWardenOfFireTarget(final PhoenixWardenOfFireTarget target) {
        super(target);
    }

    @Override
    public PhoenixWardenOfFireTarget copy() {
        return new PhoenixWardenOfFireTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        return super.canTarget(playerId, id, source, game)
                && CardUtil.checkCanTargetTotalValueLimit(
                this.getTargets(), id, MageObject::getManaValue, 6, game
        );
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        return CardUtil.checkPossibleTargetsTotalValueLimit(
                this.getTargets(),
                super.possibleTargets(sourceControllerId, source, game),
                MageObject::getManaValue, 6, game
        );
    }

    @Override
    public String getMessage(Game game) {
        // shows selected total
        int selectedValue = this
                .getTargets()
                .stream()
                .map(game::getObject)
                .filter(Objects::nonNull)
                .mapToInt(MageObject::getManaValue)
                .sum();
        return super.getMessage(game) + " (selected total mana value " + selectedValue + ")";
    }
}
